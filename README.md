# Useful Autoclicker

A versatile Autoclicker for various applications.

![image](https://github.com/Rutger505/Useful-Autoclicker/assets/119070855/3fc634ab-53f0-48e1-b8c3-8c0d98fda2a5)

## Features

* Both click and hold down the mouse button
* Control timings mentioned above.
* Randomize timings.
* Start the autoclicker by using a hotkey on your keyboard.
* Change the hotkey button.
* 5 buttons for autoclicking: left, middle, right, and the 2 side buttons.
* Limit the number of clicks to a certain amount.
* Autoclick when the seleced mouse button in pressed.

## Downloading Autoclicker

1. Download and install Java [here](https://www.java.com/). Already have Java? Skip this step.
2. Click on the latest release on the right side
   ![image](https://github.com/Rutger505/Useful-Autoclicker/assets/119070855/ab7729f1-3555-4802-9683-0dd692452e11)
3. Click on the file "Useful-Autoclicker.jar"
   ![image](https://github.com/Rutger505/Useful-Autoclicker/assets/119070855/f9b55088-b41d-4c43-803a-b3b55a527aca)
4. Open your new Autoclicker!

### Linux

On Arch, install `useful-autoclicker-git` from the AUR (`yay -S useful-autoclicker-git`). This adds a
`useful-autoclicker` command and an app launcher entry. To build it locally, run `makepkg -si` in the `aur` folder.

On other distros, run `./build.sh` (needs a JDK) and start it with `java -jar out/Useful-Autoclicker.jar`. The first
launch adds a desktop entry so it shows up in your app launcher.

## Where files are stored

* Windows: settings in `%APPDATA%\Useful-Autoclicker`, native hook library in `%LOCALAPPDATA%\Useful-Autoclicker`
* Linux: settings in `$XDG_CONFIG_HOME/useful-autoclicker` (default `~/.config`), native hook library in
  `$XDG_CACHE_HOME/useful-autoclicker` (default `~/.cache`)

## Controlling a running Autoclicker

`java -jar Useful-Autoclicker.jar --toggle` (or `useful-autoclicker --toggle` with the AUR package) toggles the clicker
of the already running Autoclicker, just like pressing the hotkey.

## Wayland / Hyprland

The Autoclicker runs through XWayland. Wayland doesn't let apps listen to global key presses, so the hotkey only works
while an XWayland window has focus. Bind the toggle in your compositor instead, for Hyprland in `hyprland.conf`:

```
bind = , F6, exec, useful-autoclicker --toggle
```

To skip starting a JVM on every press, send the command to the running Autoclicker directly:

```
bind = , F6, exec, bash -c 'echo toggle > /dev/tcp/127.0.0.1/1324'
```

Clicks are sent through XWayland too, so they only reach XWayland windows (most games, Wine/Proton, Minecraft).
"Autoclick on button hold" has the same limitation.

## Problems & Solutions

### Download the latest version of Java

1. Go to the [Java website](https://www.java.com/) and download and install Java again.

### Run the file using terminal

1. Right click on the folder where the Autoclicker is located and click "open in terminal".
2. Run the following command in the terminal:

```
java -jar "Useful-Autoclicker.jar"
```

### Opening with Java

1. Right click the Useful-Autoclicker file.
2. Click "Open with".
3. Click "Choose another app".
2. Click "Java(TM) Plantform SE Binary".
3. Click "Always".

## License

MIT, see [LICENSE](LICENSE). Bundles [JNativeHook](https://github.com/kwhat/jnativehook), licensed under the LGPL-3.0.
