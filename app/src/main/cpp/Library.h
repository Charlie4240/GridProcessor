#pragma once
#include <string>
#include <vector>
class Library { public: virtual ~Library(){}; virtual std::vector<std::string> getCandidates(const std::string&) const=0; };