.PHONY: all clean

CHARS := H e l l o W o r l d \0

define create_linked_list
$(1): linked_list
	@echo "$(CHARS)" > $@
endef

linked_list: 
	@touch linked_list

$(foreach char,$(CHARS),$(eval $(call create_linked_list,$(char))))

all: $(CHARS)

clean:
	rm -f linked_list $(CHARS)

