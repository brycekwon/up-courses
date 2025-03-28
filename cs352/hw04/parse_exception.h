#ifndef PARSE_EXCEPTION_H
#define PARSE_EXCEPTION_H

#include <sstream>
#include <string>
#include <cstring>

class parse_exception : public std::exception
{
private:
    const char* message;
public:
    parse_exception(const char* msg)
    {
        message = msg;
    }
    char* what()
    {
        std::stringstream out;
        out << "PARSE ERROR: " << message;
        std::string outstr = out.str();
        char* cstr = (char*)malloc(outstr.size());
        strcpy(cstr,outstr.c_str());
        return cstr;
    }
};
#endif // PARSE_EXCEPTION_H
