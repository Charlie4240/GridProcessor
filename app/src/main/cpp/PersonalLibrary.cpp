#include "PersonalLibrary.h"
void PersonalLibrary::train(const std::vector<std::string>& seq) {
    for(size_t i=0; i+1<seq.size(); ++i) {
        transitions[seq[i]].push_back(seq[i+1]);
    }
}
std::vector<std::string> PersonalLibrary::getCandidates(const std::string& key) const {
    auto it = transitions.find(key);
    if(it != transitions.end()) return it->second;
    return {};
}