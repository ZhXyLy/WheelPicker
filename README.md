# WheelPicker
[![](https://jitpack.io/v/ZhXyLy/WheelPicker.svg)](https://jitpack.io/#ZhXyLy/WheelPicker)
<h4>区域选择和日期选择</h4>

<b>FROM:</b>
<br>https://github.com/AigeStudio/WheelPicker 
<br>https://github.com/open-android/WheelPicker

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
  
  <h3>使用</h3>
  
    private void showAreaDialog() {
        if (wheelAreaPickerBottomDialog == null) {
            wheelAreaPickerBottomDialog = new WheelAreaPickerBottomDialog(this);
            wheelAreaPickerBottomDialog.setOnPickerAreaListener(new WheelAreaPickerBottomDialog.OnPickerAreaListener() {
                @Override
                public void onPickerArea(IWheelAreaPicker wheelAreaPicker) {
                    String province = wheelAreaPicker.getProvince().getName();
                    String city = wheelAreaPicker.getCity().getName();
                    String area = wheelAreaPicker.getArea().getName();
                    Toast.makeText(MainActivity.this, province + "-" + city + "-" + area, Toast.LENGTH_SHORT).show();
                }
            });
        }
        wheelAreaPickerBottomDialog.show();
    }

    private void showDateDialog() {
        if (wheelDatePickerBottomDialog == null) {
            wheelDatePickerBottomDialog = new WheelDatePickerBottomDialog(this);
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

    private void showWheelPicker() {
        if (wheelPickerBottomDialog == null) {
            wheelPickerBottomDialog = new WheelPickerBottomDialog(this);
            wheelPickerBottomDialog.setOnWheelPickerListener(new WheelPickerBottomDialog.OnWheelPickerListener() {
                @Override
                public void onWheelPicker(IWheelPicker wheelPicker) {
                    Object o = wheelPicker.getData().get(wheelPicker.getCurrentItemPosition());
                    Toast.makeText(MainActivity.this, o.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            wheelPickerBottomDialog.setData(Arrays.asList(TYPES));
        }
        wheelPickerBottomDialog.show();
    }
    
