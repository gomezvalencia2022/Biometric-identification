@echo off
echo =========================================
echo  Compilando control_entrada...
echo =========================================

set SRC=C:\Users\gomez\OneDrive\Documentos\control_entrada.jar.src
set DIST=C:\Gap-x\dist
set OUT=%SRC%\out
set JAR=C:\Program Files\Java\jdk-25.0.2\bin\jar.exe

:: Limpiar carpeta out
if exist "%OUT%" rmdir /s /q "%OUT%"
mkdir "%OUT%"

:: Compilar
javac -encoding UTF-8 ^
  -cp "%DIST%\dpotapi.jar;%DIST%\dpfpenrollment.jar;%DIST%\dpfpverification.jar;%DIST%\dpotjni.jar;%DIST%\ij.jar;%DIST%\postgresql.jar" ^
  -d "%OUT%" ^
  "%SRC%\control_entrada\CapturaHuella.java" ^
  "%SRC%\control_entrada\CapturaHuellaEnroler.java" ^
  "%SRC%\control_entrada\ConexionBD.java" ^
  "%SRC%\controlador\conexion.java"

if %ERRORLEVEL% neq 0 (
  echo.
  echo ERROR: La compilacion fallo. Revisa los errores arriba.
  pause
  exit /b 1
)

echo OK - Compilacion exitosa

:: Copiar recursos
if exist "%SRC%\imagenes" xcopy /s /q "%SRC%\imagenes" "%OUT%\imagenes\" >nul
if exist "%SRC%\image" xcopy /s /q "%SRC%\image" "%OUT%\image\" >nul

:: Empaquetar JAR
cd /d "%OUT%"
"%JAR%" cfm control_entrada_nuevo.jar "%SRC%\META-INF\MANIFEST.MF" .

if %ERRORLEVEL% neq 0 (
  echo.
  echo ERROR: No se pudo crear el JAR.
  pause
  exit /b 1
)

echo OK - JAR creado

:: Reemplazar en dist
copy /Y "%OUT%\control_entrada_nuevo.jar" "%DIST%\control_entrada.jar" >nul

echo OK - JAR copiado a %DIST%
echo.
echo =========================================
echo  Listo. Puedes abrir control_entrada.jar
echo =========================================
pause
