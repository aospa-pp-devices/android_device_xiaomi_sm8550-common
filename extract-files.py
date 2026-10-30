#!/usr/bin/env -S PYTHONPATH=../../../tools/extract-utils python3
#
# SPDX-FileCopyrightText: 2024 The LineageOS Project
# SPDX-License-Identifier: Apache-2.0
#

from extract_utils.fixups_blob import (
    blob_fixup,
    blob_fixups_user_type,
)
from extract_utils.fixups_lib import (
    lib_fixup_remove,
    lib_fixups,
    lib_fixups_user_type,
)
from extract_utils.main import (
    ExtractUtils,
    ExtractUtilsModule,
)

namespace_imports = [
    'hardware/xiaomi',
    'vendor/qcom/common/system/telephony',
    'vendor/qcom/common/vendor/adreno/t',
    'vendor/qcom/common/vendor/display/5.15',
]

def lib_fixup_odm_suffix(lib: str, partition: str, *args, **kwargs):
    return f'{lib}_odm' if partition in ('odm', 'vendor') else None

def lib_fixup_sm8550_suffix(lib: str, partition: str, *args, **kwargs):
    return f'{lib}_sm8550' if partition in ('odm', 'vendor') else None

def lib_fixup_vendor_suffix(lib: str, partition: str, *args, **kwargs):
    return f'{lib}_vendor' if partition in ('odm', 'vendor') else None

lib_fixups: lib_fixups_user_type = {
    **lib_fixups,
    (
        'vendor.xiaomi.hardware.fx.tunnel@1.0',
        'vendor.xiaomi.hardware.mfidoca@1.0',
        'vendor.xiaomi.hardware.mlipay@1.0',
        'vendor.xiaomi.hardware.mlipay@1.1',
        'vendor.xiaomi.hardware.mtdservice@1.0',
        'vendor.xiaomi.hardware.mtdservice@1.1',
        'vendor.xiaomi.hardware.mtdservice@1.2',
        'vendor.xiaomi.hardware.mtdservice@1.3',
        'vendor.xiaomi.hardware.tidaservice@1.0',
        'vendor.xiaomi.hardware.tidaservice@1.1',
        'vendor.xiaomi.hardware.tidaservice@1.2',
    ): lib_fixup_odm_suffix,
    (
        'audio.primary.kalama',
    ): lib_fixup_sm8550_suffix,
    (
        'com.qualcomm.qti.dpm.api@1.0',
        'com.qualcomm.qti.imscmservice@1.0',
        'com.qualcomm.qti.imscmservice@2.0',
        'com.qualcomm.qti.imscmservice@2.1',
        'com.qualcomm.qti.imscmservice@2.2',
        'com.qualcomm.qti.uceservice@2.0',
        'com.qualcomm.qti.uceservice@2.1',
        'com.qualcomm.qti.uceservice@2.2',
        'com.qualcomm.qti.uceservice@2.3',
        'vendor.qti.data.factory@2.0',
        'vendor.qti.data.factory@2.1',
        'vendor.qti.data.factory@2.2',
        'vendor.qti.data.factory@2.3',
        'vendor.qti.data.factory@2.4',
        'vendor.qti.data.factory@2.5',
        'vendor.qti.data.factory@2.6',
        'vendor.qti.data.factory@2.7',
        'vendor.qti.data.mwqem@1.0',
        'vendor.qti.data.slm@1.0',
        'vendor.qti.diaghal@1.0',
        'vendor.qti.hardware.data.cne.internal.api@1.0',
        'vendor.qti.hardware.data.cne.internal.constants@1.0',
        'vendor.qti.hardware.data.cne.internal.server@1.0',
        'vendor.qti.hardware.data.cne.internal.server@1.1',
        'vendor.qti.hardware.data.cne.internal.server@1.2',
        'vendor.qti.hardware.data.cne.internal.server@1.3',
        'vendor.qti.hardware.data.connection@1.0',
        'vendor.qti.hardware.data.connection@1.1',
        'vendor.qti.hardware.data.connectionfactory-V1-ndk',
        'vendor.qti.hardware.data.dataactivity-V1-ndk',
        'vendor.qti.hardware.data.dynamicdds@1.0',
        'vendor.qti.hardware.data.dynamicdds@1.1',
        'vendor.qti.hardware.data.flow@1.0',
        'vendor.qti.hardware.data.flow@1.1',
        'vendor.qti.hardware.data.iwlan@1.0',
        'vendor.qti.hardware.data.iwlan@1.1',
        'vendor.qti.hardware.data.ka-V1-ndk',
        'vendor.qti.hardware.data.latency@1.0',
        'vendor.qti.hardware.data.lce@1.0',
        'vendor.qti.hardware.data.qmi@1.0',
        'vendor.qti.hardware.dpmservice@1.0',
        'vendor.qti.hardware.dpmservice@1.1',
        'vendor.qti.hardware.embmssl@1.0',
        'vendor.qti.hardware.embmssl@1.1',
        'vendor.qti.hardware.limits@1.0',
        'vendor.qti.hardware.limits@1.1',
        'vendor.qti.hardware.limits@1.2',
        'vendor.qti.hardware.ListenSoundModel@1.0',
        'vendor.qti.hardware.mwqemadapter@1.0',
        'vendor.qti.hardware.qccsyshal@1.0',
        'vendor.qti.hardware.qccsyshal@1.1',
        'vendor.qti.hardware.qccsyshal@1.2',
        'vendor.qti.hardware.qccvndhal@1.0',
        'vendor.qti.hardware.qxr-V1-ndk',
        'vendor.qti.hardware.radio.am-V1-ndk',
        'vendor.qti.hardware.radio.am@1.0',
        'vendor.qti.hardware.radio.atcmdfwd-V1-ndk',
        'vendor.qti.hardware.radio.atcmdfwd@1.0',
        'vendor.qti.hardware.radio.ims-V9-ndk',
        'vendor.qti.hardware.radio.ims@1.0',
        'vendor.qti.hardware.radio.ims@1.1',
        'vendor.qti.hardware.radio.ims@1.2',
        'vendor.qti.hardware.radio.ims@1.3',
        'vendor.qti.hardware.radio.ims@1.4',
        'vendor.qti.hardware.radio.ims@1.5',
        'vendor.qti.hardware.radio.ims@1.6',
        'vendor.qti.hardware.radio.ims@1.7',
        'vendor.qti.hardware.radio.ims@1.8',
        'vendor.qti.hardware.radio.internal.deviceinfo-V1-ndk',
        'vendor.qti.hardware.radio.internal.deviceinfo@1.0',
        'vendor.qti.hardware.radio.lpa@1.0',
        'vendor.qti.hardware.radio.lpa@1.1',
        'vendor.qti.hardware.radio.lpa@1.2',
        'vendor.qti.hardware.radio.lpa@1.3',
        'vendor.qti.hardware.radio.qcrilhook-V1-ndk',
        'vendor.qti.hardware.radio.qcrilhook@1.0',
        'vendor.qti.hardware.radio.qtiradio-V9-ndk',
        'vendor.qti.hardware.radio.qtiradio@1.0',
        'vendor.qti.hardware.radio.qtiradio@2.0',
        'vendor.qti.hardware.radio.qtiradio@2.1',
        'vendor.qti.hardware.radio.qtiradio@2.2',
        'vendor.qti.hardware.radio.qtiradio@2.3',
        'vendor.qti.hardware.radio.qtiradio@2.4',
        'vendor.qti.hardware.radio.qtiradio@2.5',
        'vendor.qti.hardware.radio.qtiradio@2.6',
        'vendor.qti.hardware.radio.qtiradioconfig-V3-ndk',
        'vendor.qti.hardware.radio.uim@1.0',
        'vendor.qti.hardware.radio.uim@1.1',
        'vendor.qti.hardware.radio.uim@1.2',
        'vendor.qti.hardware.radio.uim_remote_client@1.0',
        'vendor.qti.hardware.radio.uim_remote_client@1.1',
        'vendor.qti.hardware.radio.uim_remote_client@1.2',
        'vendor.qti.hardware.radio.uim_remote_server@1.0',
        'vendor.qti.hardware.sigma_miracast@1.0',
        'vendor.qti.hardware.slmadapter@1.0',
        'vendor.qti.hardware.wifidisplaysession@1.0',
        'vendor.qti.ims.callcapability@1.0',
        'vendor.qti.ims.callinfo@1.0',
        'vendor.qti.ims.configservice@1.0',
        'vendor.qti.ims.configservice@1.1',
        'vendor.qti.ims.connection@1.0',
        'vendor.qti.ims.factory@1.0',
        'vendor.qti.ims.factory@1.1',
        'vendor.qti.ims.factory@2.0',
        'vendor.qti.ims.factory@2.1',
        'vendor.qti.ims.factory@2.2',
        'vendor.qti.ims.rcsconfig@1.0',
        'vendor.qti.ims.rcsconfig@1.1',
        'vendor.qti.ims.rcsconfig@2.0',
        'vendor.qti.ims.rcsconfig@2.1',
        'vendor.qti.ims.rcssip@1.0',
        'vendor.qti.ims.rcssip@1.1',
        'vendor.qti.ims.rcssip@1.2',
        'vendor.qti.ims.rcsuce@1.0',
        'vendor.qti.ims.rcsuce@1.1',
        'vendor.qti.ims.rcsuce@1.2',
        'vendor.qti.imsrtpservice@3.0',
        'vendor.qti.imsrtpservice@3.1',
        'vendor.qti.latency@2.0',
        'vendor.qti.latency@2.1',
        'vendor.qti.latency@2.2',
        'vendor.qti.latency@2.3',
    ): lib_fixup_vendor_suffix,
    (
        'libcamxcommonutils',
        'libwpa_client',
    ): lib_fixup_remove,
}

blob_fixups: blob_fixups_user_type = {
    'odm/etc/init/android.hardware.nfc@1.2-service-st.rc': blob_fixup()
        .regex_replace(
            'on boot && property:ro.build.version.sdk=35 && property:ro.product.device=ishtar',
            'on boot && property:ro.hardware.nfc=ST_NFC',
        )
        .regex_replace('.+setprop ro.hardware.nfc ST_NFC+\n', ''),
    (
        'odm/lib64/libaudioroute_ext.so',
        'vendor/lib64/libar-pal.so',
    ): blob_fixup()
        .replace_needed(
            'libaudioroute.so',
            'libaudioroute-v34.so',
        ),
    (
        'odm/lib64/hw/displayfeature.default.so',
        'vendor/lib64/c2.dolby.hevc.dec.so',
        'vendor/lib64/c2.dolby.hevc.enc.so',
        'vendor/lib64/c2.dolby.hevc.sec.dec.so',
        'vendor/lib64/hw/audio.primary.kalama.so',
        'vendor/lib64/libcodec2_soft_ac4dec.so',
        'vendor/lib64/libcodec2_soft_ddpdec.so',
        'vendor/lib64/libcodec2_soft_mihc_dec.so',
        'vendor/lib64/libcodec2_soft_mihc_enc.so',
        'vendor/lib64/libDecoderProcessor.so',
        'vendor/lib64/libdlbdsservice.so',
        'vendor/lib64/libdlbpreg.so',
        'vendor/lib64/libqc2audio_hwaudiocodec.so',
        'vendor/lib64/libswspatializer_ext.so',
        'vendor/lib64/soundfx/libdlbvol.so',
        'vendor/lib64/soundfx/libhwdap.so',
        'vendor/lib64/soundfx/libmisoundfx.so',
        'vendor/lib64/soundfx/libswspatializer.so',
    ): blob_fixup()
        .replace_needed(
            'libstagefright_foundation.so',
            'libstagefright_foundation-v33.so',
        ),
    'odm/lib64/libmt@1.3.so': blob_fixup()
        .replace_needed(
            'libcrypto.so',
            'libcrypto-v33.so',
        ),
    'odm/lib64/nfc_nci.nqx.default.hw.so': blob_fixup()
        .replace_needed(
            'libbase.so',
            'libbase-v33.so'
        ),
    'system_ext/framework/mirilhook.jar': blob_fixup()
        .apktool_patch('blob-patches/mirilhook.patch', '-r'),
    'vendor/bin/qguard' : blob_fixup()
        .add_needed('libbase_shim.so'),
    (
        'vendor/bin/hw/android.hardware.security.keymint-service-qti',
        'vendor/lib64/libqtikeymint.so',
    ): blob_fixup()
        .add_needed('android.hardware.security.rkp-V3-ndk.so'),
    # Viper4AndroidFX routing
    'vendor/etc/audio/sku_kalama/audio_effects.xml': blob_fixup()
        .regex_replace('<libraries>', '<libraries>\n        <library name="v4a_re" path="libv4a_re.so"/>')
        .regex_replace('<effects>', '<effects>\n        <effect name="v4a_standard_re" library="v4a_re" uuid="90380da3-8536-4744-a6a3-5731970e640f"/>'),
    'vendor/etc/seccomp_policy/c2audio.vendor.ext-arm64.policy': blob_fixup()
        .add_line_if_missing('setsockopt: 1'),
    'vendor/etc/seccomp_policy/qwesd@2.0.policy': blob_fixup()
        .add_line_if_missing('pipe2: 1'),
    'vendor/lib64/c2.dolby.client.so': blob_fixup()
        .add_needed('libcodec2_hidl_shim.so'),
    # The Dolby HEVC blobs allocate a GraphicBuffer-sized object with operator
    # new right after UnwrapNativeCodec2GrallocHandle and reserve 0x100 bytes
    # for it.  Current libui writes that object through offset 0xd28, so the
    # 256-byte allocation overruns the heap and corrupts whatever follows
    # (binder buffers, HIDL listener lists, the Adreno driver's program
    # state), crashing dolbycodec2 on the first Dolby Vision metadata frame.
    # Reserve 0x1000 instead.  The pattern pairs the mov with the following
    # operator new call to stay unique; c2.dolby.hevc.enc.so also contains an
    # unrelated "mov w0, #0x100" in C2AndroidMemoryUsage::FromGrallocUsage,
    # so a bare 4-byte pattern must not be used.
    'vendor/lib64/c2.dolby.hevc.enc.so': blob_fixup()
        .binary_regex_replace(
            b'\x00\x20\x80\x52\x03\x42\x00\x94',
            b'\x00\x00\x82\x52\x03\x42\x00\x94',
        ),
    (
        'vendor/lib64/c2.dolby.hevc.dec.so',
        'vendor/lib64/c2.dolby.hevc.sec.dec.so',
    ): blob_fixup()
        .binary_regex_replace(
            b'\x00\x20\x80\x52\x74\x54\x00\x94',
            b'\x00\x00\x82\x52\x74\x54\x00\x94',
        ),
    'vendor/lib64/libqcodec2_core.so': blob_fixup()
        .add_needed('libcodec2_shim.so'),
    'vendor/lib64/libqms_xiaomi.so': blob_fixup()
        .add_needed('libbinder_shim.so'),
    'vendor/lib64/libsnpe_config.so': blob_fixup()
        .add_needed('liblog.so'),
    'vendor/lib64/vendor.libdpmframework.so': blob_fixup()
        .add_needed('libhidlbase_shim.so'),
}  # fmt: skip

module = ExtractUtilsModule(
    'sm8550-common',
    'xiaomi',
    blob_fixups=blob_fixups,
    lib_fixups=lib_fixups,
    namespace_imports=namespace_imports,
)

if __name__ == '__main__':
    utils = ExtractUtils.device(module)
    utils.run()
