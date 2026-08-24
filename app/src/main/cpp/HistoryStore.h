#pragma once
#include <vector>
#include <cstdint>
class HistoryStore {
public:
    void recordFrame(const std::vector<uint8_t>& data);
    std::vector<std::vector<uint8_t>> retrieveHistory(int count);
private:
    std::vector<std::vector<uint8_t>> frames;
    std::vector<uint8_t> last;
};