/* filename: mylib.c */
// gcc -shared -o mylib.so -fPIC mylib.c

const char * my_function()
{
    return "Brno JUG";
}

int add(int a, int b)                                                //Function Definition
{
    return a + b;
}
