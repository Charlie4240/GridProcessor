cmake_minimum_required(VERSION 3.18)
project("gridengine")
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
add_library(gridengine SHARED
    native-lib.cpp
    GridEngine.cpp
    Node.cpp
    Gate.cpp
    Search.cpp
    Library.cpp
    PersonalLibrary.cpp
    HistoryStore.cpp
    Benchmarks.cpp
    VulkanCompute.cpp
    data_conversion.cpp
)
target_include_directories(gridengine PRIVATE ${CMAKE_CURRENT_SOURCE_DIR})
target_link_libraries(gridengine log android GLESv2 EGL)
find_library(vulkan-lib vulkan)
if(vulkan-lib)
    target_compile_definitions(gridengine PRIVATE HAVE_VULKAN)
    target_link_libraries(gridengine ${vulkan-lib})
endif()