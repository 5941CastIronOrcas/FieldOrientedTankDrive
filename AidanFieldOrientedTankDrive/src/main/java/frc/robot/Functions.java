package frc.robot;

public class Functions {

    public static double DeadZone(double input, double deadZone)
    {
        if(Math.abs(input) < deadZone)
        {
            return 0;
        }
        else
        {
            return input;
        }
        
    }

    public static double Exponential(double input)
    {
        return input * Math.abs(input);
    }
    
    public static double DeltaAngleDegrees(double startAngle, double endAngle)
    {
        return ((((endAngle - startAngle - 180) % 360) + 360) % 360)-180;
    }

    public static double Clamp(double input, double min, double max)
    {
        if(input > max)
        {
            input = max;
        }
        else if(input < min)
        {
            input = min;
        }
        return input;
    }
}
