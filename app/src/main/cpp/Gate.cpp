#include "Gate.h"
bool computeGate(const Node& node, const std::vector<Node>& allNodes) {
    float sum=0;
    for(int nid : node.neighbors) if(nid>=0) {
        const auto& nb = allNodes[nid];
        sum += nb.activation * nb.weight * (nb.binaryState ? 1.0f : 0.0f);
    }
    return sum >= node.threshold;
}