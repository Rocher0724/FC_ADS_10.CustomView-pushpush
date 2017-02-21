package choongyul.android.com.customview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CustomView view;

    ImageButton btnUp, btnDown,btnRight,btnLeft;
    FrameLayout ground;

    private static final int GROUND_SIZE = 12;
    // 플레이어 정보
    int player_x = 0;
    int player_y = 0;
    int player_radius = 0;
    // 이동단위
    int unit = 0;

    final int map[][] = {
            {1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1},
            {1,1,0,0,1,1,0,0,0,1,1},
            {1,1,2,0,0,2,0,0,0,1,1},
            {1,1,0,0,1,1,1,0,2,1,1},
            {1,1,0,1,0,0,0,1,0,1,1},
            {1,1,0,1,0,0,0,1,0,1,1},
            {1,0,2,0,0,2,0,0,2,0,1},
            {1,0,0,0,0,0,1,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1,1}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        ground = (FrameLayout) findViewById(R.id.frameLO);
        btnDown = (ImageButton) findViewById(R.id.btnDown);
        btnUp = (ImageButton) findViewById(R.id.btnUp);
        btnRight = (ImageButton) findViewById(R.id.btnRight);
        btnLeft = (ImageButton) findViewById(R.id.btnLeft);

        btnDown.setOnClickListener(this);
        btnUp.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnLeft.setOnClickListener(this);

        view = new CustomView(this);
        ground.addView(view);
    }

    private void init() {
        DisplayMetrics matrics = getResources().getDisplayMetrics();
        unit = matrics.widthPixels/GROUND_SIZE;
        player_radius = unit/2;

        player_x = 6;
        player_y = 7;
    }

    int collisionFlag = 0;

    private boolean CollisionCheck(String direction) {
        if(direction.equals("right")){
            if( map[player_y][player_x + 1] != 0) {
                collisionFlag = map[player_y][player_x + 1];
                return true; // 이동하려는곳이 0이 아닌경우 충돌했다 true!!
            }
        } else if(direction.equals("left")){
            if ( map[player_y][player_x - 1] != 0 ) {
                collisionFlag = map[player_y][player_x - 1];
                return true;
            }
        } else if(direction.equals("up") ){
            if ( map[player_y - 1][player_x] != 0) {
                collisionFlag =  map[player_y - 1][player_x];
                return true;
            }
        } else if(direction.equals("down") ){
            if ( map[player_y + 1][player_x] != 0) {
                collisionFlag = map[player_y + 1][player_x];
                return true;
            }
        }
        return false;
    }

    private boolean overCollisionCheck(String direction) { // 두칸 앞에 무언가가 있으면 트루
        switch (direction) {
            case "right":
                if( player_x < GROUND_SIZE-3) {
                    if (map[player_y][player_x + 2] != 0) {
                        return true;
                    } else {
                        map[player_y][player_x + 2] = 2;
                        map[player_y][player_x + 1] = 0;
                        return false;
                    }
                }
                break;
            case "left":
                if( player_x > 1 ) {
                    if (map[player_y][player_x - 2] != 0) {
                        return true;
                    } else {
                        map[player_y][player_x - 2] = 2;
                        map[player_y][player_x - 1] = 0;
                        return false;
                    }
                }
                break;
            case "up":
                if(player_y > 1 ){
                    if( map[player_y - 2][player_x] != 0) {
                        return true;
                    } else {
                        map[player_y - 2][player_x] = 2;
                        map[player_y - 1][player_x] = 0;
                        return false;
                    }
                }
                break;
            case "down":
                if( player_y < GROUND_SIZE-3 ) {
                    if (map[player_y + 2][player_x] != 0) {
                        return true;
                    } else {
                        map[player_y + 2][player_x] = 2;
                        map[player_y + 1][player_x] = 0;
                        return false;
                    }
                }
                break;
        }
        return false;
    }

    public int setPlusX(int data) {
        if ( CollisionCheck("right") ) { // 충돌하면 트루
            switch (collisionFlag) {
                case 1:
                    return data;
                case 2:
                    if( overCollisionCheck("right") ) {
                        return data;
                    }
                    return data + 1;
                case 3:
                    return data + 1;
            }
        } else if (data < GROUND_SIZE - 2) {
                return data + 1;
        }
        return data;
    }

    public int setMinusX(int data) {
        if ( CollisionCheck("left") ) { // 충돌하면 트루
            switch (collisionFlag) {
                case 1:
                    return data;
                case 2:
                    if( overCollisionCheck("left") ) {
                        return data;
                    }
                    return data - 1;
                case 3:
                    return data - 1;
            }
        } else if (data > 0) {
            return data - 1;
        }
        return data;
    }

    public int setPlusY(int data) {
        if ( CollisionCheck("down") ) { // 충돌하면 트루
            switch (collisionFlag) {
                case 1:
                    return data;
                case 2:
                    if( overCollisionCheck("down") ) {
                        return data;
                    }
                    return data + 1;
                case 3:
                    return data + 1;
            }
        } else if (data < GROUND_SIZE - 2) {
            return data + 1;
        }
        return data;
    }
    public int setMinusY(int data) {
        if ( CollisionCheck("up") ) { // 충돌하면 트루
            switch (collisionFlag) {
                case 1:
                    return data;
                case 2:
                    if( overCollisionCheck("up") ) {
                        return data;
                    }
                    return data - 1;
                case 3:
                    return data - 1;
            }
        } else if (data > 0) {
            return data - 1;
        }
        return data;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUp:
                player_y = setMinusY(player_y);
                break;
            case R.id.btnDown:
                player_y = setPlusY(player_y);
                break;
            case R.id.btnRight:
                player_x = setPlusX(player_x);
                break;
            case R.id.btnLeft:
                player_x = setMinusX(player_x);
                break;
        }
        view.invalidate();
        collisionFlag = 0;
        if( map[5][4] == 2
//                && map[5][5] == 2
//                && map[5][6] == 2
//                && map[6][4] == 2
//                && map[6][5] == 2
//                && map[6][6] == 2
                ) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Finish");
            dialog.setMessage("Stage Clear!!!");
            dialog.setPositiveButton("good", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton("good!!!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
    }


    class CustomView extends View {

        Paint magenta = new Paint();
        Paint blue = new Paint();
        Paint line = new Paint();
        Paint brown = new Paint();
        Paint goal = new Paint();


        public CustomView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            magenta.setColor(Color.MAGENTA);
            blue.setColor(Color.BLUE);
            line.setColor(Color.BLACK);
            line.setStrokeWidth(5);
            brown.setARGB(0xff,0xb4,72,00);
            goal.setColor(Color.YELLOW);

            canvas.drawRect(480,600,860,850,goal);

//            gray.setColor(Color.GRAY);
            canvas.drawCircle(player_x * unit + player_radius
                            ,player_y * unit + player_radius
                            ,player_radius
                            ,magenta);

            for (int i=0 ; i<map.length ; i++) {
                for( int j=0 ; j<map[0].length ; j++) {
                    // 1은 벽
                    if(map[i][j] == 1) {
                        canvas.drawRect(unit*j, unit*i, unit*j + unit, unit*i + unit, brown);
                    }
                    // 2는 공
                    if(map[i][j] == 2) {
                        canvas.drawRect(unit*j, unit*i, unit*j + unit, unit*i + unit, blue);
                    }
//                     3은 목표지점
//                    if(map[i][j] == 3) {
//                        canvas.drawRect(unit*j, unit*i, unit*j + unit, unit*i + unit, goal);
//                    }
                }
            }
            canvas.drawLine(0,0,0,1300,line);
            canvas.drawLine(0,0,1300,0,line);
            canvas.drawLine(1300,0,1300,1300,line);
            canvas.drawLine(0,1300,1300,1300,line);

        }
    }


}

