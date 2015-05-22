def scoring(array)
  array.each do |arr|
    arr.update_score unless arr.is_admin?
  end
end
