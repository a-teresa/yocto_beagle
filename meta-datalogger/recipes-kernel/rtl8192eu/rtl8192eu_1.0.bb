SUMMARY = "External RTL8192EU USB Wi-Fi kernel module"
DESCRIPTION = "Out-of-tree Realtek RTL8192EU USB Wi-Fi driver for adapters such as TP-Link TL-WN823N v2/v3."
HOMEPAGE = "https://github.com/Mange/rtl8192eu-linux-driver"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://os_dep/linux/usb_intf.c;beginline=1;endline=12;md5=2e8246ed3abbbb95c781b51b5c89857e"

inherit module

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI = "git://github.com/Mange/rtl8192eu-linux-driver.git;branch=realtek-4.4.x;protocol=https"
SRCREV = "9c0511420da11214c68d8591b19459ba10892aab"

PV = "1.0+git${SRCPV}"
S = "${WORKDIR}/git"

EXTRA_OEMAKE += " \
    KSRC=${STAGING_KERNEL_DIR} \
    KVER=${KERNEL_VERSION} \
    USER_MODULE_NAME=8192eu \
    CONFIG_PLATFORM_I386_PC=n \
    CONFIG_PLATFORM_ARM_RPI=y \
    ARCH=${ARCH} \
    CROSS_COMPILE=${TARGET_PREFIX} \
"

KERNEL_MODULE_AUTOLOAD += "8192eu"
KERNEL_MODULE_PROBECONF += "8192eu"
module_conf_8192eu = "options 8192eu rtw_power_mgnt=0 rtw_enusbss=0"

do_configure:append() {
    sed -i '/#include <drv_types.h>/a #include <net/cfg80211.h>' ${S}/core/rtw_mlme_ext.c
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/8192eu.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/8192eu.ko

    install -d ${D}${sysconfdir}/modprobe.d
    printf '%s\n' 'blacklist rtl8xxxu' > ${D}${sysconfdir}/modprobe.d/rtl8xxxu-blacklist.conf
}

FILES:${PN} += " \
    ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/8192eu.ko \
    ${sysconfdir}/modprobe.d/rtl8xxxu-blacklist.conf \
"
