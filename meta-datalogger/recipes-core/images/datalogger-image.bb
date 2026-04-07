SUMMARY = "Datalogger custom image"

inherit core-image

IMAGE_INSTALL += " \
    kernel-modules \
    openssh \
    busybox-udhcpc \
    usbinit \
    packagegroup-base-wifi \
    connman \
    connman-client \
    rtl8192eu \
"

EXTRA_IMAGE_FEATURES += "debug-tweaks ssh-server-openssh"
