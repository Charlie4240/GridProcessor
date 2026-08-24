#include "HistoryStore.h"
void HistoryStore::recordFrame(const std::vector<uint8_t>& data) {
    if(last.empty()) {
        frames.push_back(data);
    } else {
        std::vector<uint8_t> delta;
        for(size_t i=0; i<data.size(); ++i) {
            if(data[i] != last[i]) {
                delta.push_back(i & 0xFF);
                delta.push_back((i >> 8) & 0xFF);
                delta.push_back(data[i]);
            }
        }
        if(!delta.empty()) {
            std::vector<uint8_t> chunk = {0x01};
            chunk.insert(chunk.end(), delta.begin(), delta.end());
            frames.push_back(chunk);
        }
    }
    last = data;
}
std::vector<std::vector<uint8_t>> HistoryStore::retrieveHistory(int count) {
    if(count <= 0 || count > (int)frames.size()) count = frames.size();
    return std::vector<std::vector<uint8_t>>(frames.end()-count, frames.end());
}