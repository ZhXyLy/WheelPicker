# WheelPicker
[![](https://jitpack.io/v/ZhXyLy/WheelPicker.svg)](https://jitpack.io/#ZhXyLy/WheelPicker)
只用老版：1.3.0
<h4>区域选择和日期选择</h4>

<b>FROM:</b>
<br>https://github.com/AigeStudio/WheelPicker 
<br>https://github.com/open-android/WheelPicker

<h3>效果图：</h3>

<img src="/images/wheelpicker.gif" alt="效果图" width="30%" align="center">

<div>
<img src="/images/单选1.png" alt="新单选" width="30%">
<img src="/images/单选-旧版.png" alt="单选-旧版" width="30%">
<img src="/images/单选-新版.png" alt="单选-新版" width="30%">
</div>

<div>
<img src="/images/日期1.png" alt="新日期" width="30%">
<img src="/images/日期-旧版.png" alt="日期-旧版" width="30%">
<img src="/images/日期-新版.png" alt="日期-新版" width="30%">
</div>

<div>
<img src="/images/时间1.png" alt="新时间" width="30%">
<img src="/images/时间-旧版.png" alt="时间-旧版" width="30%">
<img src="/images/时间-新版.png" alt="时间-新版" width="30%">
</div>

<div>
<img src="/images/区域1.png" alt="新区域" width="30%">
<img src="/images/区域-旧版.png" alt="区域-旧版" width="30%">
<img src="/images/区域-新版.png" alt="区域-新版" width="30%">
</div>


<h3>How to</h3>
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

gradle
maven
sbt
leiningen
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.ZhXyLy:WheelPicker:latest-version'
	}
	
	
<h3>新版使用（从2.0.0开始）</h3>

    可以直接使用
    <com.jx.wheelpicker.widget.lasted.XxxPicker>
    或者
    使用Dialog

<h4>单选</h4>

    注：需要implements Data，如果是单独的String可以直接使用StringData。

    private void showSingleLastedDialog() {
        if (singlePickerDialog == null) {
            singlePickerDialog = new SinglePickerDialog.SingleBuilder(this)
                    .setData(stringData)
                    .setOnChangedListener(new SinglePickerDialog.OnChangedListener() {
                        @Override
                        public void onChanged(SinglePicker singlePicker, Data data) {
                            tvLastedResult.setText(data == null ? "没选到东西" : data.getText());
                        }
                    })
                    .setOnPickListener(new SinglePickerDialog.OnPickListener() {
                        @Override
                        public void onPicked(SinglePicker singlePicker, Data data) {
                            ToastUtils.show(data == null ? "没选到东西" : data.getText());
                        }
                    })
                    .build();
        }
        singlePickerDialog.show();
    }


<h4>日期选择</h4>


    private void showLastedDialog() {
            if (mPickerDialog == null) {
                mPickerDialog = new DatePickerDialog.DateBuilder(this)
                        .setOnDateChangedListener(new DatePickerDialog.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker datePicker, int year, int month, int day, String week) {
                                tvLastedResult.setText(String.format("%s-%s", datePicker.getDateString(), week));
                            }
                        })
                        .setOnPickerDateListener(new DatePickerDialog.OnPickerDateListener() {
                            @Override
                            public void onPickerDate(DatePicker datePicker, int year, int month, int day, String week) {
                                ToastUtils.show(String.format("%s-%s", datePicker.getDateString(), week));
                            }
                        })
                        .build();
            }
            mPickerDialog.show();
        }


<h4>时间选择</h4>


    private void showLastedDialog() {
            if (mPickerDialog == null) {
                mPickerDialog = new TimePickerDialog.TimeBuilder(this)
                        .setOnTimeChangedListener(new TimePickerDialog.OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimePicker timePicker, int hour, int minute, int second) {
                                tvLastedResult.setText(timePicker.getTimeString());
                            }
                        })
                        .setOnPickTimeListener(new TimePickerDialog.OnPickTimeListener() {
                            @Override
                            public void onTimePicked(TimePicker timePicker, int hour, int minute, int second) {
                                ToastUtils.show(timePicker.getTimeString());
                            }
                        })
                        .build();
            }
            mPickerDialog.show();
        }


<h4>区域选择</h4>


    private void showLastedDialog() {
            if (mPickerDialog == null) {
                mPickerDialog = new AreaPickerDialog.AreaBuilder(this)
                        .setShortText(true)
                        .setOnAreaChangedListener(new AreaPickerDialog.OnAreaChangedListener() {
                            @Override
                            public void onAreaChanged(AreaPicker areaPicker, Province province, City city, Area area) {
                                tvLastedResult.setText(areaPicker.getAreaString(" "));
                            }
                        })
                        .setOnPickerAreaListener(new AreaPickerDialog.OnPickerAreaListener() {
                            @Override
                            public void onPickerArea(AreaPicker areaPicker, Province province, City city, Area area) {
                                ToastUtils.show(areaPicker.getAreaString("-"));
                            }
                        })
                        .build();
            }
            mPickerDialog.show();
        }

  
  <h3>旧版使用</h3>
  <h4>区域选择</h4>
  
    private void showAreaDialog() {
	if (wheelAreaPickerBottomDialog == null) {
            wheelAreaPickerBottomDialog = new WheelAreaPickerBottomDialog(this);
            wheelAreaPickerBottomDialog.setSelectPositionByCode(viewModel.getSsqCode());
            wheelAreaPickerBottomDialog.setOnWheelScrollChangedListener(iWheelAreaPicker -> {
                Province province = iWheelAreaPicker.getProvince();
                City city = iWheelAreaPicker.getCity();
                Area area = iWheelAreaPicker.getArea();
                String ssq = province.getName() + city.getName() + area.getName();
                Toast.makeText(MainActivity.this, ssq, Toast.LENGTH_SHORT).show();
            });
	    //同样，点确认回调
	    //wheelAreaPickerBottomDialog.setOnPickerAreaListener(new WheelAreaPickerBottomDialog.OnPickerAreaListener() {
            //    @Override
            //    public void onPickerArea(IWheelAreaPicker wheelAreaPicker) {
            //        String province = wheelAreaPicker.getProvince().getName();
            //        String city = wheelAreaPicker.getCity().getName();
            //        String area = wheelAreaPicker.getArea().getName();
            //        Toast.makeText(MainActivity.this, province + "-" + city + "-" + area, Toast.LENGTH_SHORT).show();
            //    }
            });
        }
        wheelAreaPickerBottomDialog.show();
    }
    
<h4>日期选择</h4>

    private void showDateDialog() {
    	//和上边的一样，两种Lisenter
        if (wheelDatePickerBottomDialog == null) {
            wheelDatePickerBottomDialog = new WheelDatePickerBottomDialog(this);
	    wheelDatePickerBottomDialog.setTitle(R.string.expect_review_date);
            wheelDatePickerBottomDialog.setSelectPositionByDate(date);//由于各种日期格式，所以传入Date
            wheelDatePickerBottomDialog.setOnPickerDateListener(new WheelDatePickerBottomDialog.OnPickerDateListener() {

                @Override
                public void onPickerDate(IWheelDatePicker wheelDatePicker) {
                    String stringDate = wheelDatePicker.getStringDate("yyyy年MM月dd日");
                    Toast.makeText(MainActivity.this, stringDate, Toast.LENGTH_SHORT).show();
                }
            });
        }
        wheelDatePickerBottomDialog.show();
    }
    
<h4>时间选择</h4>
 //时间和日期的类似，自己琢磨用
<h4>单选选择</h4>	

    private void showWheelPicker() {
	if (typeWheelPickerBottomDialog == null) {
                typeWheelPickerBottomDialog = new WheelPickerBottomDialog(this);
                typeWheelPickerBottomDialog.setVisibleCount(5);//默认7个
                typeWheelPickerBottomDialog.setData(dailyTypes);
                typeWheelPickerBottomDialog.setTitle("选择类型");//小标题，也可以StringRes
                typeWheelPickerBottomDialog.setSelectPosition(viewModel.getDailyTypeName());//默认选中，通过显示文字equals来判断
		//监听OnWheelScrollChangedListener，滚动每一个都回调，
		//如果点击确认再回调，监听OnWheelPickerListener
                typeWheelPickerBottomDialog.setOnWheelScrollChangedListener((wheelPicker, o, pickerName, position) -> {
                    Toast.makeText(MainActivity.this, pickerName, Toast.LENGTH_SHORT).show();
                });
            }
            typeWheelPickerBottomDialog.show();
    }
    
