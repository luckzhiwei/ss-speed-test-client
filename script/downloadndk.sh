#!/bin/bash
ret=$(ls ./android-ndk-r16b/ | wc -w);
echo $ret
if [ $ret == "0" ]; then
echo "download ndk";
wget http://dl.google.com/android/repository/android-ndk-r16b-linux-x86_64.zip -O android-ndk-r16b-linux-x86_64.zip && unzip -q android-ndk-r16b-linux-x86_64.zip
fi