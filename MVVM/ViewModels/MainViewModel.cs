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

        public SettingViewModel HomeVM { get; set; }

        public HelpView HelpVM { get; set; }


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

        public MainViewModel()
        {
            HomeVM = new SettingViewModel();
            HelpVM = new HelpView();
            CurrentView = HelpVM;
        }
    }
}
