#!/bin/bash
adb connect 192.168.0.158 && \
adb root && \
adb connect 192.168.0.158 && \
adb remount

