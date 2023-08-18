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
        private bool _shouldRepeat;

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
            get { return ShouldRepeat ? RepeatAmount.ToString() : "# times"; }
            set
            {
                if (ShouldRepeat)
                {
                    RepeatAmount = int.Parse(value);
                    OnPropertyChanged();
                }
            }
        }

        private int _repeatAmount = 100;

        public int RepeatAmount
        {
            get { return _repeatAmount; }
            set
            {
                _repeatAmount = value;
                OnPropertyChanged();
            }
        }
    }
}
