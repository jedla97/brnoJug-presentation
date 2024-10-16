package brno.jug.presentation.jdk23;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.file.Paths;

@Path("jep454")
public class Jep454Resource {

    // http://127.0.0.1:8080/jep454/strlen
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/strlen")
    public String exception() throws Throwable {
        // Link java with OS/processor
        Linker linker = Linker.nativeLinker();
        // Load lib and find function inside that lib
        SymbolLookup stdlib = linker.defaultLookup();
        MemorySegment strlenAddress = stdlib.find("strlen")
                .orElseThrow(() -> new RuntimeException("strlen not found"));

        // STRLEN (const char *str)
        // of(Result, arguments)
        FunctionDescriptor descriptor = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS);

        // Used to handle communication between java and native lib
        MethodHandle strlen = linker.downcallHandle(strlenAddress, descriptor);

        try (Arena arena = Arena.ofConfined()) {
            String inputString = "Brno JUG";
            // Allocate memory from input string
            MemorySegment str = arena.allocateFrom(inputString);
            // call strlen function with one input parameter
            return "Number char in " + inputString + " is " + (int) strlen.invoke(str);
        }
    }

    // http://127.0.0.1:8080/jep454/own-lib
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/own-lib")
    public String ownLib() throws Throwable {
        try (Arena arena = Arena.ofConfined()) {
            // Link java with OS/processor
            Linker linker = Linker.nativeLinker();

            // Load lib and find function inside that lib
            SymbolLookup lib = SymbolLookup.libraryLookup(Paths.get("myCLib", "mylib.so"), arena);
            MemorySegment function = lib.find("my_function")
                    .orElseThrow(() -> new RuntimeException("my_function not found"));

            // Used to handle communication between java and native lib
            MethodHandle myFunction = linker.downcallHandle(function, FunctionDescriptor.of(ValueLayout.ADDRESS));

            // calling the native function
            MemorySegment pointer = (MemorySegment) myFunction.invoke();

            // Recreate the memory segment with specific size and get string
            return "Welcome on " + pointer.reinterpret(10).getString(0);
        }
    }

    // http://127.0.0.1:8080/jep454/own-lib-add
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/own-lib-add")
    public String ownLibAdd() throws Throwable {
        try (Arena arena = Arena.ofConfined()) {
            // Link java with OS/processor
            Linker linker = Linker.nativeLinker();

            // Load lib and find function inside that lib
            SymbolLookup lib = SymbolLookup.libraryLookup(Paths.get("myCLib", "mylib.so"), arena);
            MemorySegment function = lib.find("add")
                    .orElseThrow(() -> new RuntimeException("add not found"));

            FunctionDescriptor descriptor = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT);
            // Used to handle communication between java and native lib
            MethodHandle myFunction = linker.downcallHandle(function, descriptor);

            int num1 = 5;
            int num2 = 4;

            // calling the native function with numbers as argument
            int pointer = (int) myFunction.invoke(num1, num2);

            return "The sum of " + num1 + " and " + num2 + " is " + pointer;
        }
    }

}
