cmd /c "call "C:\Program Files (x86)\Microsoft Visual Studio\2017\BuildTools\VC\Auxiliary\Build\vcvars64.bat" && mvn package -Pnative -Dquarkus.native.additional-build-args="--initialize-at-run-time=javax.imageio.ImageTypeSpecifier\,com.sun.imageio.plugins.jpeg.JPEG\$JCS""