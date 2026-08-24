#include "VulkanCompute.h"
#include <android/log.h>
bool VulkanCompute::init() { __android_log_print(ANDROID_LOG_INFO, "GPU", "Vulkan not available"); return false; }
bool VulkanCompute::isAvailable() const { return false; }