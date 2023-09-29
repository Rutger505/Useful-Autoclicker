using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Threading;

namespace Useful_Autoclicker.Core.Autoclicker
{
    internal class InputListener
    {

        private const int WM_HOTKEY = 0x0312;

        // Modifier key constants
        private const int MOD_ALT = 0x0001;
        private const int MOD_CTRL = 0x0002;
        private const int MOD_SHIFT = 0x0004;

        // Delegate and external function for handling hotkeys
        private delegate void HotkeyHandlerDelegate(int id);

        [DllImport("user32.dll")]
        private static extern bool RegisterHotKey(IntPtr hWnd, int id, int fsModifiers, int vk);

        [DllImport("user32.dll")]
        private static extern bool UnregisterHotKey(IntPtr hWnd, int id);

        // Console window handle
        private IntPtr consoleHandle;


        public InputListener() // Pass your custom logger to the constructor
        {
            consoleHandle = Process.GetCurrentProcess().MainWindowHandle;
        }

        public bool RegisterHotkey(int id, ConsoleKey key, ConsoleModifiers modifiers)
        {
            int modifierMask = 0;

            if ((modifiers & ConsoleModifiers.Alt) != 0)
                modifierMask |= MOD_ALT;

            if ((modifiers & ConsoleModifiers.Control) != 0)
                modifierMask |= MOD_CTRL;

            if ((modifiers & ConsoleModifiers.Shift) != 0)
                modifierMask |= MOD_SHIFT;

            int virtualKeyCode = (int)key;

            return RegisterHotKey(consoleHandle, id, modifierMask, virtualKeyCode);
        }

        public bool UnregisterHotkey(int id)
        {
            return UnregisterHotKey(consoleHandle, id);
        }

        public void ListenForHotkey(ConsoleKey key, ConsoleModifiers modifiers, Action hotkeyAction)
        {
            int hotkeyId = key.GetHashCode() ^ modifiers.GetHashCode();
            Logger.Trace($"Listening for hotkey: {key} with modifiers: {modifiers}");

            if (RegisterHotkey(hotkeyId, key, modifiers))
            {
                Logger.Info("Hotkey registered successfully.");
                while (true)
                {
                    Logger.Trace("ListenForHotkey while true loop");
                    int msg = 0;
                    if (PeekMessage(out msg, IntPtr.Zero, WM_HOTKEY, WM_HOTKEY, 1))
                    {
                        Logger.Trace($"PeekMessage result: {msg}");
                        if (msg == WM_HOTKEY)
                        {
                            Logger.Trace($"Hotkey triggered: {key} with modifiers: {modifiers}");
                            hotkeyAction();
                        }
                    }
                    Thread.Sleep(3); // Adjust sleep time as needed
                }
            }
            else
            {
                Logger.Error($"Failed to register hotkey: {key} with modifiers: {modifiers}");
            }
        }

        [DllImport("user32.dll")]
        private static extern bool PeekMessage(out int msg, IntPtr hWnd, uint filterMin, uint filterMax, uint remove);

        private bool PeekMessage(out int msg, IntPtr hWnd, int filterMin, int filterMax, int remove)
        {
            msg = 0;
            return PeekMessage(out msg, hWnd, (uint)filterMin, (uint)filterMax, (uint)remove);
        }

    }
}
