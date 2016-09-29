package sincere.com.sidebardeamo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SideBar sideBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sideBar= (SideBar) findViewById(R.id.sideBar);
        sideBar.setDialogText(this,R.layout.list_position);
    }
}
