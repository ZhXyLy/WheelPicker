# WheelPicker
[![](https://jitpack.io/v/ZhXyLy/WheelPicker.svg)](https://jitpack.io/#ZhXyLy/WheelPicker)
<h4>区域选择和日期选择</h4>

<b>FROM:</b>
<br>https://github.com/AigeStudio/WheelPicker 
<br>https://github.com/open-android/WheelPicker

###效果图：

![效果图](/images/wheelpicker.gif)


<h3>效果图：</h3>

<img src="https://github.com/ZhXyLy/WheelPicker/blob/master/images/%E7%9C%81%E5%B8%82%E5%8C%BA%E9%80%89%E6%8B%A9.jpg" 
alt="省市区" width="280">
<img src="https://github.com/ZhXyLy/WheelPicker/blob/master/images/%E6%97%A5%E6%9C%9F%E9%80%89%E6%8B%A9.jpg" 
alt="日期" width="280">
<img src="https://github.com/ZhXyLy/WheelPicker/blob/master/images/%E5%8D%95%E9%80%89.jpg" 
alt="单选" width="280">


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
	
	
###新版2

  
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
    
