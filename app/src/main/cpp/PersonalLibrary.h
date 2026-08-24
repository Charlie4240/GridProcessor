#pragma once
#include "Library.h"
#include <unordered_map>
class PersonalLibrary : public Library {
public:
    void train(const std::vector<std::string>& seq);
    std::vector<std::string> getCandidates(const std::string&) const override;
private:
    std::unordered_map<std::string, std::vector<std::string>> transitions;
};