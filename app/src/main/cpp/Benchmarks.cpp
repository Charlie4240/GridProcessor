#include "Benchmarks.h"
#include <chrono>
std::string runBenchmarks() {
    auto start = std::chrono::steady_clock::now();
    auto end = std::chrono::steady_clock::now();
    return "CPU time: " + std::to_string(std::chrono::duration_cast<std::chrono::milliseconds>(end-start).count()) + " ms";
}