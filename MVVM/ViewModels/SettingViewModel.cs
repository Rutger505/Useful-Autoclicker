using Useful_Autoclicker.Core;

namespace Useful_Autoclicker.MVVM.ViewModels;

internal class SettingViewModel : ObservableObject
{
    private int _repeatAmount;

    private bool _shouldAutoclickOnHold;
    private bool _shouldRepeat;

    private bool _shouldUseHotkey = true;

    public bool ShouldRepeat
    {
        get => _shouldRepeat;
        set
        {
            _shouldRepeat = value;
            OnPropertyChanged();
            OnPropertyChanged(nameof(RepeatTextBoxText));
        }
    }


    public string RepeatTextBoxText
    {
        get
        {
            if (ShouldRepeat) return RepeatAmount.ToString();

            return RepeatAmount == 0 ? "# times" : RepeatAmount.ToString();
        }
        set
        {
            if (ShouldRepeat)
            {
                if (int.TryParse(value, out var result))
                    RepeatAmount = result;
                else
                    RepeatAmount = 0;
                OnPropertyChanged();
            }
        }
    }

    public int RepeatAmount
    {
        get => _repeatAmount;
        set
        {
            _repeatAmount = value;
            OnPropertyChanged();
        }
    }

    public bool ShouldAutoclickOnHold
    {
        get => _shouldAutoclickOnHold;
        set
        {
            _shouldAutoclickOnHold = value;
            ShouldUseHotkey = !value;
        }
    }

    public bool ShouldUseHotkey
    {
        get => _shouldUseHotkey;
        set
        {
            _shouldUseHotkey = value;
            OnPropertyChanged();
        }
    }
}
