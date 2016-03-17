def convert_to_celsius t, input_scale
    case input_scale.to_sym
        when :celsius then t
        when :kelvin then t-273.15
        when :fahrenheit then (t-32)/1.8
    end
end

def convert_temp t, input_scale: 'celsius', output_scale: 'celsius'
    return t if input_scale == output_scale
    
    t1 = convert_to_celsius(t, input_scale)
    
    case output_scale.to_sym
        when :celsius then t1
        when :kelvin then t1+273.15
        when :fahrenheit then t1*1.8+32
    end
end
