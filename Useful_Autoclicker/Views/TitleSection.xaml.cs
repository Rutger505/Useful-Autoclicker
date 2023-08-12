using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Useful_Autoclicker.Views
{
    /// <summary>
    /// Interaction logic for TitleSection.xaml
    /// </summary>
    public partial class TitleSection : UserControl
    {
        public static readonly DependencyProperty _title =
            DependencyProperty.Register("Title", typeof(string), typeof(TitleSection), 
                new PropertyMetadata(string.Empty));

        public string Title
        {
            get { return (string)GetValue(_title); }
            set { SetValue(_title, value); }
        }

        public TitleSection()
        {
            InitializeComponent();
        }
    }
}
