using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Useful_Autoclicker.Views
{
    public partial class ClickHoldInterval
    {
        public static readonly DependencyProperty _rowText =
            DependencyProperty.Register("RowText", typeof(string), typeof(ClickHoldInterval),
                new PropertyMetadata(string.Empty));

        public string RowText
        {
            get { return (string)GetValue(_rowText); }
            set { SetValue(_rowText, value); }
        }

        public ClickHoldInterval()
        {
            InitializeComponent();
        }
    }
}
