using System;
using Useful_Autoclicker.Core;
using Useful_Autoclicker.MVVM.Views;

namespace Useful_Autoclicker.MVVM.ViewModels
{
    internal class MainViewModel : ObservableObject
    {

        public SettingViewModel HomeViewModel { get;  }

        public HelpView HelpViewModel { get;  }

        public RelayCommand HelpViewCommand { get; }

        private object _currentView;

        public object CurrentView
        {
            get { return _currentView; }
            set
            {
                _currentView = value;
                OnPropertyChanged();
            }
        }

        public bool HelpCheckBoxChecked { get; set; } = false;


        public MainViewModel()
        {
            HomeViewModel = new SettingViewModel();
            HelpViewModel = new HelpView();
            CurrentView = HomeViewModel;

            HelpViewCommand = new RelayCommand(o =>
            {
                if (HelpCheckBoxChecked)
                {
                    CurrentView = HelpViewModel;
                }
                else
                {
                    CurrentView = HomeViewModel;
                }
            });
        }
    }
}
