namespace Useful_Autoclicker;

/// <summary>
///     Interaction logic for MainWindow.xaml
/// </summary>
public class MainWindow : Window
{
    public MainWindow()
    {
        InitializeComponent();
    }


    private void DragWindow(object sender, MouseButtonEventArgs e)
    {
        DragMove();
    }

    private void CloseWindow(object sender, RoutedEventArgs e)
    {
        Close();
    }

    private void MinimizeWindow(object sender, RoutedEventArgs e)
    {
        WindowState = WindowState.Minimized;
    }
}
