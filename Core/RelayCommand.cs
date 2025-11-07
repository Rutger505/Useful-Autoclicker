using System;
using System.Windows.Input;

namespace Useful_Autoclicker.Core;

internal class RelayCommand : ICommand
{
    private readonly Func<object, bool> _canExecute;
    private readonly Action<object> _execute;

    public RelayCommand(Action<object> execute, Func<object, bool> canExecute = null)
    {
        _execute = execute;
        _canExecute = canExecute;
    }

    public event EventHandler CanExecuteChanged
    {
        add => CommandManager.RequerySuggested += value;
        remove => CommandManager.RequerySuggested -= value;
    }

    public bool CanExecute(object obj)
    {
        return _canExecute == null || _canExecute(obj);
    }

    public void Execute(object obj)
    {
        _execute(obj);
    }
}
