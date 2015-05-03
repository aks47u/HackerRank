def kaprekar(k)
    k_squared = (k * k).to_s
    idx = k_squared.length / 2
    return (k_squared[0..idx - 1].to_i + k_squared[idx.. - 1].to_i == k) || (k == 1) 
end

p = gets().strip.to_i
q = gets().strip.to_i
kaprekars = (p..q).map{ | x | x.to_i}.select{ | x | kaprekar(x)}
if kaprekars.length == 0
    puts "INVALID RANGE"
else
    puts kaprekars.map{ | x | x.to_s}.join(" ")
end
