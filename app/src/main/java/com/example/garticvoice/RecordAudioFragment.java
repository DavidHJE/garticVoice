package com.example.garticvoice;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.garticvoice.databinding.FragmentRecordAudioBinding;

import java.io.IOException;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordAudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordAudioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MediaRecorder recorder;
    private FragmentRecordAudioBinding binding;

    public RecordAudioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordAudioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordAudioFragment newInstance(String param1, String param2) {
        RecordAudioFragment fragment = new RecordAudioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRecordAudioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.BtnStartRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecording();
            }
        });

        binding.BtnStopRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
            }
        });
    }

    private void startRecording() {

        String uuid = UUID.randomUUID().toString();
        String fileName = "/sdcard/Music/" + uuid + ".3gp";

        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        recorder.setOutputFile(fileName);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed" + e);
        }
        recorder.start();
        binding.RecorderTextView.setText("enregistrement commence");
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        binding.RecorderTextView.setText("enregistrement se termine");
    }
}