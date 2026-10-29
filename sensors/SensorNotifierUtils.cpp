/*
 * Copyright (C) 2024 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

#define LOG_TAG "SensorNotifierUtils"

#include <android-base/logging.h>
#include <malloc.h>

#include <cstring>
#include <vector>

#include "SensorNotifierUtils.h"

bool readBool(int fd) {
    char c;
    int rc;

    rc = lseek(fd, 0, SEEK_SET);
    if (rc) {
        LOG(ERROR) << "failed to seek fd, err: " << rc;
        return false;
    }

    rc = read(fd, &c, sizeof(char));
    if (rc != 1) {
        LOG(ERROR) << "failed to read bool from fd, err: " << rc;
        return false;
    }

    return c != '0';
}

std::shared_ptr<disp_event_resp> parseDispEvent(int fd) {
    // The OS3.0.x kernel's mi_disp driver expects the whole event to be
    // consumed in a single read() call: a read smaller than the event
    // length silently drops the event and returns 0. Read with a buffer
    // large enough for the header plus any payload instead of issuing
    // separate reads for the header and the data.
    std::vector<uint8_t> buf(kMaxDispEventSize);
    ssize_t eventSize = read(fd, buf.data(), buf.size());
    if (eventSize < static_cast<ssize_t>(sizeof(disp_event))) {
        LOG(ERROR) << "unexpected display event size: " << eventSize;
        return nullptr;
    }

    auto* header = reinterpret_cast<disp_event*>(buf.data());
    if (header->length < sizeof(disp_event) ||
        static_cast<ssize_t>(header->length) > eventSize) {
        LOG(ERROR) << "invalid display event length: " << header->length;
        return nullptr;
    }

    std::shared_ptr<disp_event_resp> response(
            static_cast<disp_event_resp*>(malloc(header->length)), free);
    if (!response) {
        LOG(ERROR) << "failed to allocate memory for display event response";
        return nullptr;
    }
    memcpy(response.get(), buf.data(), header->length);

    return response;
}
