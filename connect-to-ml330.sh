#!/bin/bash
IP=192.168.0.154
adb connect $IP && \
adb root && \
adb connect $IP && \
adb remount

