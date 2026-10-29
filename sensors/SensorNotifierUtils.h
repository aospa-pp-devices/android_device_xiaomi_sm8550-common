/*
 * Copyright (C) 2024 The LineageOS Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

#pragma once

#include <cstddef>
#include <memory>

#include <mi_disp.h>

// Upper bound for a display event; the kernel drops events that do not
// fit into a single read() call, so the buffer must cover header plus
// any payload.
constexpr size_t kMaxDispEventSize = 256;

bool readBool(int fd);
std::shared_ptr<disp_event_resp> parseDispEvent(int fd);
