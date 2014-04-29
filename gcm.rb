require 'gcm'

gcm = GCM.new('AIzaSyB92IqFcbjE6Q5fy9YYm5oXORioc7jwkGk')
regids = ['APA91bE7CsXY1ZeKu3PNon0L1Q-7uscK8Tk6Gt0NYd11SNFaam5usBxNKKRx-U57b7PIucTG0dDQEcOjpsNVAPnLWnlPKXdqG3l0D85rCUSeE_HzFL-6n0i-FBEXD0fxhixBvK9_yIIjRoI2VEEUoG4A7AYGvwamqQ']
respons = gcm.send_notification(regids)
