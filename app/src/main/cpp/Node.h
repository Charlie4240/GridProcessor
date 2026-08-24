#pragma once
#include <array>
#include <cstdint>
struct Node {
    int id, x, y, z;
    uint8_t binaryState;
    float activation, threshold;
    bool gateOpen;
    std::array<int,6> neighbors;
    float weight;
    int patternId, libraryId, visitCount;
    uint8_t previousState, nextState;
    float candidateScore;
};