package base.splithighlightseekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private OnlyHeSeekBar onlyHeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onlyHeSeekBar = findViewById(R.id.seekBar);
        onlyHeSeekBar.setMax(Constants.DURATION);
        onlyHeSeekBar.setProgress(0);
        Button btnChange = findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onlyHeSeekBar.isOnlyHe()) {
                    onlyHeSeekBar.setProgressBackground(false, null);
                } else {
                    onlyHeSeekBar.setProgressBackground(true, initStartEndPointsData());
                }
            }
        });
    }

    private List<StartEndPoint> initStartEndPointsData() {
        List<StartEndPoint> startEndPoints = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            StartEndPoint startEndPoint = new StartEndPoint();
            int delta = random.nextInt(10);
            startEndPoint.start = 10 * i;
            startEndPoint.end = startEndPoint.start + delta;
            startEndPoints.add(startEndPoint);
        }
        return startEndPoints;
    }
}
