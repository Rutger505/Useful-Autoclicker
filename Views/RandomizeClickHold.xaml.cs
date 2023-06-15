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
    /// Interaction logic for RandomizeClick.xaml
    /// </summary>
    public partial class RandomizeClickHold : UserControl
    {
        public static readonly DependencyProperty _rowText =
            DependencyProperty.Register("RowText", typeof(string), typeof(RandomizeClickHold),
                new PropertyMetadata(string.Empty));

        public string RowText
        {
            get { return (string)GetValue(_rowText); }
            set { SetValue(_rowText, value); }
        }

        public RandomizeClickHold()
        {
            InitializeComponent();
        }
    }
}
