using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Useful_Autoclicker.Models
{
    public static class SettingsDefault
    {
        private static readonly int[] _clickDelay = { 0, 0, 0, 90 };
        private static readonly int[] _holdDelay = { 0, 0, 0, 90 };
        private static readonly int _clicks = 199;


        public static int[] ClickDelay
        {
            get { return _clickDelay; }
        }
        public static int[] HoldDelay
        {
            get { return _holdDelay; }
        }

        public static int Clicks
        {
            get { return _clicks; }
        }

    }
}
