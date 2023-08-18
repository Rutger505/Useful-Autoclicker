using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using Useful_Autoclicker.Core;
using Useful_Autoclicker.MVVM.Views;

namespace Useful_Autoclicker.MVVM.ViewModels
{
    internal class MainViewModel : ObservableObject
    {

        public SettingViewModel HomeViewModel { get; set; }

        public HelpView HelpViewModel { get; set; }

        public RelayCommand HelpViewCommand { get; set; }


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

            CurrentView = HomeViewModel;
        }
    }
}
