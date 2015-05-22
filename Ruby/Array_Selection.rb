def select_arr(arr)
    arr.select{|n| n % 2 != 0}
end

def reject_arr(arr)
    arr.reject{|n| n % 3 == 0}
end

def delete_arr(arr)
    arr.delete_if{|n| n < 0}
end

def keep_arr(arr)
    arr.delete_if{|n| n >= 0}
end
