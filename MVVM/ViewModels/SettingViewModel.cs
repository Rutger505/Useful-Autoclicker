using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Useful_Autoclicker.Core;

namespace Useful_Autoclicker.MVVM.ViewModels
{
    internal class SettingViewModel : ObservableObject
    {
        private bool _shouldRepeat = false;

        public bool ShouldRepeat
        {
            get { return _shouldRepeat; }
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
                if (ShouldRepeat)
                {
                    return RepeatAmount.ToString();
                }
                else
                {
                    return RepeatAmount == 0 ? "# times" : RepeatAmount.ToString();
                }

            }
            set
            {
                if (ShouldRepeat)
                {
                    if (int.TryParse(value, out int result))
                    {
                        RepeatAmount = result;
                    }
                    else
                    {
                        RepeatAmount = 0;
                    }
                    OnPropertyChanged();
                }
            }
        }

        private int _repeatAmount;

        public int RepeatAmount
        {
            get { return _repeatAmount; }
            set
            {
                _repeatAmount = value;
                OnPropertyChanged();
            }
        }

        private bool _shouldAutoclickOnHold = false;

        public bool ShouldAutoclickOnHold
        {
            get { return _shouldAutoclickOnHold; }
            set
            {
                _shouldAutoclickOnHold = value;
                ShouldUseHotkey = !value;
            }
        }

        private bool _shouldUseHotkey = true;

        public bool ShouldUseHotkey
        {
            get { return _shouldUseHotkey; }
            set
            {
                _shouldUseHotkey = value;
                OnPropertyChanged();
            }
        }


    }
}
