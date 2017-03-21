# FC_ADS_10.CustomView-pushpush
CustomView를 활용하여 푸시푸시 게임을 간단하게 모방해보았습니다.


```
    @Override
    public void onClick(View v) {
        // 각각의 뷰를 이동시킨 뒤 invalidate로 다시그려주는 동작을 입력해야한다.   
        view.invalidate();
    }
    // 뷰를 상속받은 커스텀뷰 클래스를 생성
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

            //만들어준 Paint에 setColor로 색깔을 입력. 미리 세팅된 색이나 #FFFFFF으로도 입력가능.
            magenta.setColor(Color.MAGENTA);
            blue.setColor(Color.BLUE);
            line.setColor(Color.BLACK);
            line.setStrokeWidth(5);
            brown.setARGB(0xff,0xb4,72,00);
            goal.setColor(Color.YELLOW);

            // 사각형을 그림. 변수는 좌측상단의 x, y 좌표와 우측하단의 x, y 좌표를 순서대로 나타낸다.
            // 5번째 변수는 색이다.
            canvas.drawRect(480,600,860,850,goal);

            // 원을 그림
            // 각각의 변수는 원의 중심의 x, y 좌표와 반지름, 색을 나타낸다.
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
                }
            }

            // 변수는 drawRect와 같이 좌측상단의 x,y 좌표, 우측하단의 x, y좌표 , 색 을 나타낸다.
            canvas.drawLine(0,0,0,1300,line);
            canvas.drawLine(0,0,1300,0,line);
            canvas.drawLine(1300,0,1300,1300,line);
            canvas.drawLine(0,1300,1300,1300,line);

        }
    }
    
```