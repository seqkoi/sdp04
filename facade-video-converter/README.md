# Video Conversion Facade

Assignment 4 for ShP-2216 Software Design Patterns (Astana IT University): the Facade structural pattern in Java 17.

## Idea

Converting a video needs five collaborating subsystem classes that must be called in the right order. `VideoConversionFacade` hides this behind one method:

```java
VideoConversionFacade converter = new VideoConversionFacade();
ConversionResult result = converter.convert("holiday.avi", "mp4");
```

A second, separate `MediaInspectionFacade` exposes read-only file inspection so the main facade stays focused.

## Structure

```
src/main/java/kz/aitu/converter
  subsystem/   ContainerReader, BitrateReader, CodecFactory, Codec, DeflateCodec,
               H264Codec, Vp9Codec, XvidCodec, AudioMixer, ContainerWriter, value types
  facade/      VideoConversionFacade, MediaInspectionFacade, ConversionResult, MediaInfo, ConversionException
  client/      Main, SampleFileGenerator
docs/          uml-class-diagram.png
```

## Conversion flow

1. `ContainerReader` splits the file into video and audio streams
2. `BitrateReader` measures stream bitrates
3. `CodecFactory` picks a codec for the target format
4. The codec encodes the video stream
5. `AudioMixer` normalizes the audio stream
6. `ContainerWriter` writes the converted file

## Run

IntelliJ IDEA: open the folder, mark `src/main/java` as Sources Root, run `kz.aitu.converter.client.Main`.

Command line (JDK 17+):

```
javac -d out $(find src -name "*.java")
java -cp out kz.aitu.converter.client.Main
```

Windows PowerShell:

```
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object FullName
javac -d out $files
java -cp out kz.aitu.converter.client.Main
```
