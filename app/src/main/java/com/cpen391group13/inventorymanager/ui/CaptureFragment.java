package com.cpen391group13.inventorymanager.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.CaptureResponseBody;
import com.cpen391group13.inventorymanager.api.service.CaptureService;
import com.cpen391group13.inventorymanager.api.service.RetrofitService;
import com.cpen391group13.inventorymanager.helpers.CategoryHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CaptureFragment extends Fragment {
    @BindView(R.id.capture_button) Button captureButton;
    @BindView(R.id.capture_texture) TextureView textureView;

    private CaptureService client;
    private Location location;
    private CameraManager cameraManager;
    protected CameraDevice cameraDevice;
    private int cameraFacing;
    private TextureView.SurfaceTextureListener surfaceTextureListener;
    private Size previewSize;
    private String cameraId;
    private CameraDevice.StateCallback stateCallback;
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;
    private Object cameraCaptureSession;
    private CaptureRequest.Builder captureRequestBuilder;
    protected CaptureRequest captureRequest;
    private File galleryFolder;

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capture, container, false);
        ButterKnife.bind(this, view);

        // Inflate the layout for this fragment
        getActivity().setTitle("Capture");

        Retrofit retrofit = RetrofitService.getClient(this.getContext());
        client = retrofit.create(CaptureService.class);

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        cameraFacing = CameraCharacteristics.LENS_FACING_BACK;

        createImageGallery();

        surfaceTextureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                setUpCamera();
                openCamera();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        };

        stateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                CaptureFragment.this.cameraDevice = cameraDevice;
                createPreviewSession();
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                cameraDevice.close();
                CaptureFragment.this.cameraDevice = null;
            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice, int i) {
                cameraDevice.close();
                CaptureFragment.this.cameraDevice = null;
            }
        };

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                RequestBody dtBody =
                        RequestBody.create(MediaType.parse("text/plain"), dt);
                RequestBody latBody =
                        RequestBody.create(MediaType.parse("text/plain"), String.valueOf(location.getLatitude()));
                RequestBody lngBody =
                        RequestBody.create(MediaType.parse("text/plain"), String.valueOf(location.getLongitude()));

                FileOutputStream outputPhoto = null;
                try{
                    outputPhoto = new FileOutputStream(createImageFile(galleryFolder));
                    textureView.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputPhoto);
                } catch (Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        if (outputPhoto != null){
                            outputPhoto.close();
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                // add image to request body
                File image = new File(galleryFolder + "/photo_capture.jpg");
                Log.d("File stored", image.getAbsolutePath());
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), image);
                MultipartBody.Part imageBody =
                        MultipartBody.Part.createFormData("file", image.getName(), requestFile);

                Call<CaptureResponseBody> call = client.mobileCapture(dtBody, latBody, lngBody, imageBody);
                call.enqueue(new Callback<CaptureResponseBody>() {
                    @Override
                    public void onResponse(Call<CaptureResponseBody> call, Response<CaptureResponseBody> response) {
                        if (response.isSuccessful()) {
                            String category = CategoryHelper.getCategoryById(response.body().getCategoryId());
                            Toast.makeText(getActivity(), "Capture Successful, Category = " + category, Toast.LENGTH_SHORT).show();
                            Log.d("Capture", response.message());
                        } else {
                            Toast.makeText(getActivity(), "Bad Request", Toast.LENGTH_SHORT).show();
                            try {
                                Log.d("Capture", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<CaptureResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Capture Failed", Toast.LENGTH_SHORT).show();
                        Log.d("Capture", t.getLocalizedMessage());
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        openBackgroundThread();
        if (textureView.isAvailable()) {
            setUpCamera();
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        closeCamera();
        closeBackgroundThread();
    }

    private void setUpCamera(){
        try{
            for (String cameraId : cameraManager.getCameraIdList()){
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == cameraFacing){
                    StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                    // getOutputSizes return array of size objects and 0th index is the highest available resolution
                    previewSize = streamConfigurationMap.getOutputSizes(SurfaceTexture.class)[0];
                    this.cameraId = cameraId;
                }
            }
        } catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    private void openCamera(){
        try{
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                cameraManager.openCamera(cameraId, stateCallback, backgroundHandler);
            }
        } catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    private void openBackgroundThread() {
        backgroundThread = new HandlerThread("camera_background_thread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    private void closeCamera(){
        if (cameraCaptureSession != null){
            //cameraCaptureSession.close();
            cameraCaptureSession = null;
        }

        if (cameraDevice != null){
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    private void closeBackgroundThread(){
        if (backgroundHandler != null){
            backgroundThread.quitSafely();
            backgroundThread = null;
            backgroundHandler = null;
        }
    }

    private void createPreviewSession(){
        try{
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(previewSurface);

            cameraDevice.createCaptureSession(Collections.singletonList(previewSurface), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    if(cameraDevice == null){
                        return;
                    }
                    try{
                        captureRequest = captureRequestBuilder.build();
                        cameraCaptureSession.setRepeatingRequest(captureRequest, null, backgroundHandler);
                        CaptureFragment.this.cameraCaptureSession = cameraCaptureSession;

                    } catch (CameraAccessException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {}

            }, backgroundHandler);
        } catch (CameraAccessException e){
            e.printStackTrace();
        }
    }

    private File createImageFile(File galleryFolder) throws IOException{
        String imageFileName = "photo_capture";
        return new File(galleryFolder + "/" + imageFileName + ".jpg");
    }

    private void createImageGallery(){
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        galleryFolder = new File(storageDirectory, getResources().getString(R.string.project_id));
        if (!galleryFolder.exists()){
            boolean created = galleryFolder.mkdirs();
            if (!created){
                Log.d("Storage", "Couldn't create folder");
            }
        }
    }
}
