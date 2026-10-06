#!/usr/bin/env bash
# Builds out/Useful-Autoclicker.jar with the same layout as the IntelliJ artifact, using only a JDK.
set -euo pipefail

root="$(cd "$(dirname "$0")" && pwd)"
out="$root/out"
classes="$out/classes"
jnativehook="$root/libraries/jnativehook-2.2.2.jar"

rm -rf "$classes"
mkdir -p "$classes"

javac --release 8 -Xlint:-options -encoding UTF-8 -d "$classes" -cp "$jnativehook" -sourcepath "$root/src" "$root/src/Main.java"
# Only loaded on Wayland, so the rest of the jar keeps running on Java 8
javac --release 17 -encoding UTF-8 -d "$classes" -cp "$classes" "$root"/src/wayland/*.java
cp -r "$root/src/resources" "$classes/"
rm "$classes"/resources/*.java

(cd "$classes" && jar xf "$jnativehook" && rm -rf META-INF)
jar cfm "$out/Useful-Autoclicker.jar" "$root/src/META-INF/MANIFEST.MF" -C "$classes" .

echo "Built $out/Useful-Autoclicker.jar"
